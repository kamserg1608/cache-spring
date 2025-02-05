# redis-ui
http://localhost:8001/redis-stack/browser

## docker official
https://hub.docker.com/r/redis/redis-stack-server


Redis + redis insight
[How to start Redis locally with Docker & Get started with Redis Insight](https://www.youtube.com/watch?v=4vWAMMFjwd0&t=723s)

LettuceConnectionFactory в качестве RedisConnectionFactory
https://docs.spring.io/spring-data/redis/reference/redis/template.html
также используется в реактивщине
https://www.baeldung.com/spring-data-redis-reactive


1) пример пропертей в aplication
2) пример простеньких ключей
   redisTemplate.opsForValue().set()
https://www.baeldung.com/spring-data-redis-properties


jedis - JedisConnectionFactory
https://www.baeldung.com/spring-data-redis-tutorial
пример на эту либу но кода нет
[Redis за 20 минут](https://www.youtube.com/watch?v=QpBaA6B1U90&t=545s)
команды из видоса, плис строчка для поднятия докера 
[COMMAND_REDIS.md](dev/COMMAND_REDIS.md)




[What is Redis Cache || Redis For Caching || Redis with Spring Boot || Redis Cache# || Green Learner](https://www.youtube.com/playlist?list=PLq3uEqRnr_2EGtGlFBPeGtL-ccsJY_8EJ)
https://github.com/codefarm0/Redis_Cache-With-SpringBoot


Статьи от Ильи
[Как в 3 раза снизить затраты на отказоустойчивую инфраструктуру, переехав с Hazelcast на Redis](https://www.youtube.com/watch?v=JQpPXXPFhPg)
https://habr.com/ru/companies/alfa/articles/737630/
фулл статья в notion
https://sprytin.notion.site/sprytin/Alfa-Backend-Stories-Redis-b757ed6384f84e00a8ad46133529fac5#0da2f5e0ce3f49bfa5d1239510aa2672
Spring АйО
https://habr.com/ru/companies/spring_aio/articles/829054/

пример
https://github.com/myfrei/redis_key_listener