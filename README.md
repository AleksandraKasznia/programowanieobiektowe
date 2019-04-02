An application created during object oriented programming course at my university.
It's an implementation of a structure like data frame. The biggest parts of this programm are an implementation of a functionality similar to
groupby on a database, making it multi-threading or being able to delegate counting to designated servers and recieving results
(users send data with certain request to one server which implements thread pool. The server distributes tasks to calculation servers
that do the work, they send results back to the main server and from there it goes back to the user).
Since it was my programming class some of the mechanism might not be the most efficient ones but we were learning certain things through
implementing them this way.
