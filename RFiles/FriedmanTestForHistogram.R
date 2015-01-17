#File should be in proper format as described by Compare Histogram
dataA<-read.csv('FriedManTest.csv',header=F)
attach(dataA)
str(dataA)
dataM<-as.matrix(dataA)
fried<-friedman.test(dataM)
#That gives p value for friedman test
fried[3]
