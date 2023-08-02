Feature: Podcast Search

  Scenario Outline: Validate Podcast Information
    Given the podcast "<podacstName>"
    When I check the number of episodes in season 1
    Then it should have 10 episodes

    When I check the title of Season 6 episode 5
    Then it should be "Massacre at the Tree of Life Synagogue | JE"

    When I check the number of seasons
    Then it should have 14 seasons

    When I check the genre of the podcast
    Then it should fall under the "Personal Growth" genre
    Examples:
      | podacstName |
