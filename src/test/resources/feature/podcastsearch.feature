Feature: Podcast Search

  Scenario Outline: Validate Podcast Information
    Given the podcast "<podcastName>"
    When I check the number of episodes in season "<season>"
    Then it should have "<episodes>" episodes
    Examples:
      | podcastName         | season | episodes |
      | Something Was Wrong |  Season 1    | 10       |
