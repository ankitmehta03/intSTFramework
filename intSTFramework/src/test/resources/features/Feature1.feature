Feature: Title of your feature
  I want to use this template for my feature file

   Scenario: Test  login with valid credentials
     Given open "chrome" and start application
     When I click on Login
     When I enter valid "username" and valid "password"
     Then I click on Loginbutton
     Then User should be able to login successfully
     
     
     