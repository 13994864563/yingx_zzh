package com.baizhi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;


@Configuration   //说明这是一个配置类
@Aspect         //说明他是一个切面类
public class CacheAspectHash {

    @Resource
    private RedisTemplate redisTemplate;

    //环绕通知
    //@Around("execution(* com.baizhi.service.*Impl.query*(..)) && !execution(* com.baizhi.service.*Impl.queryByUsername(..))")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("======环绕通知=======");

        //序列化乱码问题
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);

        //拼接字符串
        StringBuilder builder = new StringBuilder();

        //获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        builder.append(methodName);
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            builder.append(arg);
        }
        //hash中的小key
        String key = builder.toString();

        //判断key是否存在
        HashOperations opsForHash = redisTemplate.opsForHash();
        Boolean aBoolean = opsForHash.hasKey(className, key);

        Object result = null;
        if(aBoolean){
            //key存在   从redis中取出缓存数据  并返回
            result = opsForHash.get(className, key);
        }else{
            //key不存在 没有缓存
            try {
                //放行方法  返回数据
                result = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            opsForHash.put(className,key,result);
        }
        return result;
    }

    //后置通知    注解：（）中指定注解的全限定名
    //@After("@annotation(com.baizhi.annotation.DelCache)")
    public void delCahe(JoinPoint joinPoint){
        System.out.println("......后置通知.....");


        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);
    }
}
