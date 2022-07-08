# P3_Trailmates_Backend

---
## Default User Story:
A user logs on to trailmates.com looking for a buddy to hike, they search up a specific trail in the hiking search bar.<br>
Then the user flags that they want to do the hike and see other people that want to do the hike in a similar time frame.<br>
The user then messages another user asking to go on a hike. The other user gets a notification from the original user<br>
who messaged them, and they set up the hiking plan from there.

## MVP
#### USER
- User can create an account
- User can login
- User profile and user page
- User can edit profile
- User can interact with and view other user pages
- User can view a list of hikes they are interested in on their page
- User has a hiking ability rating
- User can post info on hikes they have been on, on their page
#### HIKING TRAILS
- A user can search for a particular hiking trail
- A user can examine a trail for more information
- A user can flag the trail as one they would want to hike on and give available times
- After flagging a trail that a user wants to hike, other users who have flagged the same trail with overlapping schedules will be shown
- Trail difficulty rating

<img src = "src/main/resources/ERD.png" width="800"/>

## Technologies
<table>
    <tbody>
        <tr> <td>Java</td> <td>Core Language</td> </tr>
        <tr> <td>Postman</td> <td>Endpoint Testing</td> </tr>
        <tr> <td>J-Unit</td> <td rowspan="2">Unit Testing</td> </tr>
        <tr> <td>Mockito</td> </tr>
        <tr> <td>AWS RDS</td> <td>Data Persistence</td> </tr>
        <tr>
            <td>National Park Service API<a href="https://www.nps.gov/subjects/developer/api-documentation.htm"><sup>1</sup></a></td>
            <td>External API for<br>trail information</td>
        </tr>
    </tbody>
</table>

### Contributors
#### Team Lead: Matthew Berkheimer
Ethan Despres, Alec Guarino, Will Marquardt, Brayan Aviles