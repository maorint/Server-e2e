Feature: User Registration

  @intendu1
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

  @intendu
  @UsingBrowser
  Scenario: Login as home player
    Given I'm a player
    When I log into intendu server on home login page
    Then I get dashboard screen

