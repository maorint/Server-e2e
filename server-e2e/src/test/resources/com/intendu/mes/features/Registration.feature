Feature: User Registration

  @intendu
  Scenario: Login as organization manager
    Given I'm an organization manager
    When I log into intendu server
    Then I should get my user info


  @intendu1
  @UsingBrowser
  Scenario: Login as organization player
    Given I'm a player
    When I log into intendu server on login page
    Then I get dashboard screen

  @intendu1
  @UsingBrowser
  Scenario: Login as home player
    Given I'm a player
    When I log into intendu server on home login page
    Then I get dashboard screen

  @intendu1
  @UsingBrowser
  Scenario: Login as a player using email
    Given I'm a player
    When I log into intendu with email
    Then I get dashboard screen


  @intendu1
  @UsingBrowser
  @NoCleanup
  Scenario: Register therapist and login
    Given I'm logged in as org admin
    When I add a new therapist
    Then The therapist get a registration mail
