When we need to comparing features of data in dataset,such as weather data, housing price ,etc , we will face an issue when the features are on drastically  scales.
For example, a dataset of weather. Two potential features data might be the place of country, and the tempreture in this place. then when we try to predict the weather and compares data points, the feature with the larger scale will completely dominate the other.The goal of normalization is to make every datapoint have the same scale so each feature is equally important. 

Types of normalization 

1-Min-max normalization : For each feature value , the minimum value transformed into  0, the maximum value  transformed into  1, and  other values  transformed into a decimal between 0 and 1. The formula for Min-Max Normalization  is : (value-min)/(max-min)

2-1-Z-Score Normalization:For each feature value ,If a value is equal to the mean of values, it will be normalized to 0. If it is below the mean, it will be a negative number, and if it is above the mean it will be a positive number. Z-Score Handles outliers issues(the feature distributed in outlier ), but does not produce normalized data with the exact same scale. The formula for Z-score normalization is : (value-mean)/standardDeviation.
