# tiny_url

A spring boot based tiny url restful service and web application

Spring Boot + Spring Data Redis + Spring Data JPA + Spring Security + Spring Scheduler

## Implemented functionality
- Created a web app under /app endpoint with below functionality
    - Register, login and logout
    - Create new short url with random generated hash or customized short url
    - Manage my url history and able to delete
    - Redirect the short url to its original url
    - A scheduler to clean up expired entries daily and on read if it detects expiration, it will return error to user and delete the entry asynchrounously.
- Created a restful api endpoint /api with below endpoint
    - post /api/urls
    - get /api/urls/{shortUrlHash} 
    - delete /api/urls/{shortUrlHash}
    - get /api/urls

![tiny url](https://github.com/mengqianshasha/tiny-url/blob/main/tinyurl.png)

## Build
- mvn spring-boot:run

## To-do
- Add the functionality of running realtime analytics for how many redirect occurs etc.

## Deployment
- Deployed via AWS Beanstalk
    - http://tinyurl-env.eba-yf93cwth.us-west-2.elasticbeanstalk.com/
- Also tried with deployment with Heroku using [Heroku-Maven-Plugin](https://github.com/heroku/heroku-maven-plugin) so that I don't need a remote heroku repository to be able to deploy
   - Just run mvn heroku:deploy -Pheroku
   - Changed to postgresql for that env cuz that's what it supported
   - Needs to change the jdbc url for postgresql cuz it start with postgres instead of postgresql on Heroku
   - https://mytinyurl-springboot.herokuapp.com/ 

## Docker-Compose
- Use docker compose for local external dependency management aka. mysql and redis
- install Docker Desktop
- $ docker-compose -f docker-compose.yml up &
- $ docker ps (to check if the process has been started)



