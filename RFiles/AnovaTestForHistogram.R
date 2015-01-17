#Copy data to default path of R. currently it is My document
dataA<-read.csv('AnovaTest.csv',header=F)
attach(dataA)
str(dataA)
aoc<-aov(V2~V1)
summary(aoc)
prValue<-summary(aoc)[[1]][["Pr(>F)"]][[1]]
prValue