package nilH.easyTrackDiet.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import nilH.easyTrackDiet.dto.TierData;

@Configuration
public class RedisConfig {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public ReactiveHashOperations<String, String, TierData> hashOperations(ReactiveRedisConnectionFactory redisConnectionFactory){
        RedisSerializationContext.RedisSerializationContextBuilder<String,TierData> contextBuilder=RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        ReactiveRedisTemplate<String,TierData> template=new ReactiveRedisTemplate<String,TierData>(redisConnectionFactory, 
        contextBuilder.hashKey(new GenericToStringSerializer<>(String.class)).hashValue(new Jackson2JsonRedisSerializer<>(TierData.class)).build());
        return template.opsForHash();
    }

}
