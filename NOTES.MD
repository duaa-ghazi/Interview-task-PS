- Describe in your own words what is z-score and Min-Max scaling and why would we need to use such normalization methods? describe by example, if possible.
```
When we need to comparing features of data in dataset,such as weather data, housing price ,etc , we will face an issue when the features are on drastically  scales.
For example, a dataset of weather. Two potential features data might be the place of country, and the tempreture in this place. then when we try to predict the weather and compares data points, the feature with the larger scale will completely dominate the other.The goal of normalization is to make every datapoint have the same scale so each feature is equally important. 

Types of normalization 

1-Min-max normalization : For each feature value , the minimum value transformed into  0, the maximum value  transformed into  1, and  other values  transformed into a decimal between 0 and 1. The formula for Min-Max Normalization  is : (value-min)/(max-min)

2-1-Z-Score Normalization:For each feature value ,If a value is equal to the mean of values, it will be normalized to 0. If it is below the mean, it will be a negative number, and if it is above the mean it will be a positive number. Z-Score Handles outliers issues(the feature distributed in outlier ), but does not produce normalized data with the exact same scale. The formula for Z-score normalization is : (value-mean)/standardDeviation.
```

- We used Junit5 in our test cases, describe in your own words why would we use such library? 
what is unit testing and why would we use it?
```
JUnit is a unit testing  for Java .JUnit tests allow us to write codes faster, which allow us to check   the functionality of an application . we need this to do  testing first then coding then testing then coding and so on . we first write known input and an expected output .We must include all cases for each requirement   positive tests and  negative tests.For Java applications, JUnit 5 is the most commonly used testing framework.
```
- What is the IDE did you use?
```
Intellij IDEA
```

- Name the Java APIs you used?
```
I used :
java.math.BigDecimal;
java.math.MathContext;
java.math.RoundingMode;
java.io.*;
java.math.BigDecimal;
java.math.MathContext;
java.nio.file.Path;
java.util.ArrayList;
java.util.Arrays;
java.util.List;
java.util.stream.Collectors;
```

- Mention down any difficulties or concerns you faced while working on the task. Your feedback is appreciated :D.
```
Nothing :)
```

- If you implement any of the bonus points, please mentioned it in this file
```
Executable jar file. Which located in (\out\artifacts\iterview_task_1_0_SNAPSHOT_jar\iterview-task-1.0-SNAPSHOT.jar)
```

- Did this task enrich technical skills or knowledge? If yes, tell us how: 
```
Yes , I understood more about the unit testing .Also about BigDecimal .It was also very fun and uncomplicated.
```
