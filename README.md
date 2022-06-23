# CS-360
Inventory Application using Recycler View for Android (version 28+)




Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

The requirements of this project were to develop a functional android application for inventory control. This application also
contains an sqlLite database for storing user information and inventory information for persistent storage. This application
allows a user to login, add and remove inventory items and modify the quantities. This application will also notify (via SMS) a user
of low stock. While the application is not typesafe in its current iteration, this was a demonstraion of applied techniques and not
necessarily a market/user ready project.

What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

A login/signup screen, a inventory modification, a screen for adding and removing inventory, and a messages screen were required for this application.
My UI considered the simpliest approach for a user to easily learn functionality. While ideally the Icons would be custom in a final design the current
icons are generally adequate. My design allows a user to do each task without a complex hierarchy and limit the exposure to of a mistake to one item,
in a real world application this allows an organization to limit the detrimental impact an individual user can have, and with the logging screen (not currently
implemented, though the data is available) the organization would be able to determine the issue and rollback the mistake as required. 

How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

My process of coding the application was to do what layout the data structures needed and then to implement them in the best way feasible at the time.
While there are a list of improvements (for example the user class) I have in mind for this application they were not necessary for the projects functionality.
For this design and layout the designs were made previously (by me) so implementing the layouts was a relatively straightforward process. The techniques
I used in particular were making functional code, then cleaning that code uup to make it more readible or maintainable in the future. As it stands there are
still a few areas I would like to cleanup in future applications (specifically reassigning views) that were frankinsteined together as the usage of the recycler view
was still a relatively unfamilar area for me (though I am happy generally with my outcome in its design). 

How did you test to ensure your code was functional? Why is this process important and what did it reveal?

I tested each screen extensively (manually) before moving on to the next screen. Often if I made an improvement on one screen I would go back and 
modify a previously built screen to incorporate that improvement. While this was clearly not the most efficient approach it allowed me to learn
alot more than I would have if I maintained the same design throughout the project, and while there were definitely easier ways to implement my design
I figured it was more important to learn than it was to use outdated and deprecated layout schemes and design patterns. This process revealed a number
of bugs and weird issues I otherwise may not have seen or been able to remedy easily if I would have delayed testing until the project was near complete. 

Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

In particular my biggest issue was developing the recycler view and gaining all the functionality needed (including the popup window). While I definitely
have learned more and could likely build a better application now (in less time) the process still worked generally speaking. I actually did have to leave
a specific feature (expanding inventory views) out in the project, I am fairly certain I can now solve the problem with what I learned throughout the process.
Additionally, adding the database functionality needed was a task I underestimated as I was not very familiar with SQL at the initial stages of development.

In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

I think the overall recycler view usage demonstates my knowledge and skills as it was not a mandatory part of the project and also is probably
the most complicated to develop portion of the project (and the most complicated part of android development I have encountered). Pulling data from
the recycler view also took a level of technical understanding that I think was a little above general expectations, though in hindsight, the process
is not nearly as difficult to solve going forward. 
