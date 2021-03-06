# **Quiz Project**
The quiz game project. The system consists of players and judges. Players reply to various questions. Judges review the players' answers and decide whether the questions were answered correctly or not. If the answer is correct the team gets a point. Services: time - tracks the time between the amount of time the team has spent on the question; hints - captains can request a hint to assist their teams; statistics - players can view the amount of correct answers they provided after the judge finishes his review. The game's format is assigned by configuration. Configuration dictates the amount of time and questions the team has during the game.
#

Система "Что? Где? Когда?". Система состоит из знатоков и судьи. Знатоки отвечают на вопросы разного типа. Судья принимает ответы от знатоков, опередляет правильность ответа, и защитывает очко команде знатоков (в случае правильного ответа). Сервисы: время - засекает время между началом и концом вопроса в соответствии с конфигурацией; подсказки: - дает текстовую подсказку (заранее прикреплена к вопросу). Статистика - отображает статистику после окончания игры. Формат определяется конфигурацией. Конфигурация управляет настройками системы: время и количество вопросов у команды.
#
### Requirements:
- JDK 8+;
- Apache Maven;
- Apache Tomcat;
- MySQL server;

### Technology Stack:
1. Java 8;
2. Maven;
3. Servlets;
4. Log4J;
5. JUnit;
6. JSP + JSTL library;
7. Tomcat;
8. JDBC;
9. Mockito;
10. MySQL
#
### Schema: 
![](https://i.imgur.com/WE98wjO.png)
#
### How to deploy the project:
1. Clone the git repository to your PC.
2. Run the DB deployment scripts from the resources folder. 
3. Build the .war file using Maven (utilise the "mvn clean package -DskipTests" command).
4. Deploy .war file to your local server environment (Tomcat).
