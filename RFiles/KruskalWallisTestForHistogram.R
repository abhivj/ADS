dataA<-read.csv('AnovaTest.csv',header=F)
attach(dataA)
kruskal.test(V2 ~ V1)