# Testing PrestaShop using Cucumber
This directory contains the cucumber files for testing the checkout module of PrestaShop.

## Running the tests
Run ```mvn test``` to run all the tests.

## Feature files
The behaviors that we tested are in the feature files that inside the [src/test/resources/prestashopCucumber](src/test/resources/hellocucumber) directory. See the files for a detailed description of the tests.

Make sure that the text inside the feature files is informative and self-explanatory. This includes the "Feature: ...", "Scenario: ...", and "Given/When/Then ..." lines. See the "example.feature" file for an example.

## Step files
The step files in the [src/test/java/prestashopCucumber](src/test/java/hellocucumber) directory contain the code that defines how each sentence in the feature files is translated to Selenium actions. See the files for a detailed description of the implementation.

Make sure that each step is documented and properly written (meaningful variable names, no magic number, etc.). See the [StepDefinitions.java](src/test/java/prestashopCucumber/StepDefinitions.java) file for an example.