# Example application
to prove https://github.com/spring-projects/spring-data-redis/issues/2396

## Background

The version of `spring-data-redis` that ships with Spring-Boot 2.7.x has changed the implementation of
`GenericJackson2JsonRedisSerializer` in a strange way.  For example:
-  a UUID like `730145fe-324d-4fb1-b12f-60b89a045730` used to be serialized - as one might expect, for JSON! - as
   `"730145fe-324d-4fb1-b12f-60b89a045730"`
-  now, the same UUID gets serialized in a strange array, that looks instead like
   `["java.util.UUID","730145fe-324d-4fb1-b12f-60b89a045730"]`

## Demonstrate the change in behaviour

1. `mvn clean test` - the tests run fine, this is Spring-Boot 2.6.x mode
2. Change the `spring-boot.version` property in pom.xml to use Spring-Boot 2.7.x
3. `mvn clean test` - the tests fail
4. Switch use of `TEST_UUID_BYTES_SPRING_BOOT_2_6_x` to instead `TEST_UUID_BYTES_SPRING_BOOT_2_7_x` - the tests pass again
