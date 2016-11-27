Feature: User Blocks

  @intenduok
  Scenario: Send one block
    Given I'm logged in player in the system
    And I started a new game session
    When I send one block
    Then block is stored in server

  @intenduok
  Scenario: Send one block using old api
    Given I'm logged in player in the system
    And I started a new game session
    When I send one block with startdate value
    Then block is stored and startdate is ignored

  @intenduok
  Scenario: Send one block using new api
    Given I'm logged in player in the system
    And I started a new game session
    When I use new api to send one block with startdate value
    Then block is stored in server

  @intenduok
  Scenario: Send bulk of blocks
    Given I'm logged in player in the system
    And I started a new game session
    When I send bulk of blocks
    Then All blocks are stored in server


  @intenduok
  Scenario: Send disordered blocks
    Given I'm logged in player in the system
    And I started a new game session
    When I send bulk of disordered blocks
    Then All blocks are stored in right order in server

  @intenduok
  Scenario: Send disordered blocks
    Given I'm logged in player in the system
    And I started a new game session
    When I send disordered blocks one by one
    Then All blocks are stored in right order in summary