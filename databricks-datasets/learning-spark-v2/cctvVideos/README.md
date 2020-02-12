========================================================
[CAVAIR Test Case Scenarios: Clips from INRIA (1st Set)]
========================================================

This data set was obtained from http://homepages.inf.ed.ac.uk/rbf/CAVIAR/.  The source of this data is: EC Funded CAVIAR project/IST 2001 37540.


=========================================
Data Set Information
=========================================

The dataset structure is as follows:

## Clips from INRIA (1st Set) from the CAVAIR Test Case Scenarios[1]:
```
* /databricks-datasets/cctvVideos/train/
* /databricks-datasets/cctvVideos/test/
```


## Derived from the above datasets
All other folders contain dataset derived from the above Clips from INRIA (1st Set) from the CAVIAR Test Case Scenarios as described below.
```
/databricks-datasets/cctvVideos/mp4/  			# MP4 videos generated from the above videos
/databricks-datasets/cctvVideos/labels/			# Manually created labels categorizing suspicious images
/databricks-datasets/cctvVideos/train_images	# Hive-style partitioning of labelled images
```


## MP4 version of videos
The MP4 videos stored in `/databricks-datasets/cctvVideos/mp4/` were created by Databricks using the following command.
```
brew install ffmpeg

for x in *.MPG; do
	ffmpeg -i $x -strict experimental -f mp4 \
	       -vcodec libx264 -acodec aac \
           -ab 160000 -ac 2 -preset slow \
	       -crf 22 ${x/.MPG/.mp4};
done
``` 


## Labels
Stored within `/databricks-datasets/cctvVideos/labels/`; these are manually created labels to identify which images (extracted from the training videos) are considered suspicious per the blog post [Identify Suspicious Behavior in Video with Databricks Runtime for Machine Learning](https://databricks.com/blog/2018/09/13/identify-suspicious-behavior-in-video-with-databricks-runtime-for-machine-learning.html)


## Training Labeled Images
Stored within `/databricks-datasets/cctvVideos/train_images`; these are images labeled using Hive-style partitioning where `label=0` denote non-suspicious images and `label=1` denote suspicious images per the previously noted **Labels** section. 


=========================================
Citation
=========================================

Applicable citations:
1. EC Funded CAVIAR project/IST 2001 37540, found at URL: http://homepages.inf.ed.ac.uk/rbf/CAVIAR/