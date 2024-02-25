Acceptance criteria:

As an api consumer, given username and header “Accept: application/json”, I would like to list all his GitHub repositories, which are not forks. Information, which I require in the response, is:



Repository Name

Owner Login

For each branch it’s name and last commit sha



As an api consumer, given not existing GitHub user, I would like to receive 404 response in such a format:

{

    “status”: ${responseCode}

    “message”: ${whyHasItHappened}

}



Notes:

Please fulfil the given acceptance criteria, delivering us your best code compliant with industry standards.

Please use https://developer.github.com/v3 as a backing API

Application should have a proper README.md file