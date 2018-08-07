# Lunch Picker
Lunch picker is an app to help you and your friends pick a next restaurant at
which to have a lunch.

## Assumptions & Shortcuts
1. **Everyone can add/remove/update restaurants**. It is assumed that even an
anonymous user is allowed to add a restaurant.
2. **No intricate user system is needed**. That is, any request that is issued
by a non-existing user simply creates that user in a user table. Additionally,
no authentication, authorization, etc. is needed.
3. **There can be no more than 999.99 votes.** No particular reason for this,
it just did not seem that a higher limit for a toy project would be needed.
4. **There can be no more than 65535 unique votes.** Again, no particular
reason, it could very well be `BIGINT`, but that seems a bit too much for this
project.