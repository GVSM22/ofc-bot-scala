# ofc-bot-scala
Discord Bot using Scala, Slick and Ackord

Tools required for building:
- Scala (2.13.5)
- SBT (1.4.7)
- Docker/Docker Compose

You'll need a token from discord to start the bot

Running Database
- docker-compose up (postgres will start at 5432)

Building
- sbt clean
- sbt compile

Running
- sbt run Main
