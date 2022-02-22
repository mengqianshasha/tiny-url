# tiny_url

A spring boot based tiny url restful service and web application

Spring Boot + Spring Data Redis + Spring Data MySql + Spring Security + Spring Scheduler

## Implemented functionality

- Create new short url with random generated hash or customized short url
- Manage my url history and able to delete
- Redirect the short url to its original url
- A scheduler to clean up expired entries daily and on read if it detects expiration, it will return error to user and delete the entry asynchrounously.

## Deployment
- Deployed via AWS Beanstalk
- Also tried with deployment with Heroku using Heroku-Maven-Plugin so that we don't need a remote heroku repository to be able to deploy
   - Just run mvn heroku:deploy -Pheroku
   - Changed to postgresql for that env cuz that's what it supported
   - Needs to change the jdbc url for postgresql cuz it start with postgres instead of postgresql
   - https://mytinyurl-springboot.herokuapp.com/ 

## Docker-Compose
- Use docker compose for local deployment to start mysql and redis
- docker-compose -f .\docker-compose.yml up


