attr_0_1 <- runif(400,min=2,max=8)
attr_0_2 <- runif(400,min=4,max=15)
attr_0_3 <- runif(400,min=14,max=36)
attr_0_4 <- runif(400,min=1,max=50)
attr_0_5 <- rnorm(400,mean=1,sd=5)
attr_0_6 <- rnorm(400,mean=15,sd=3)
attr_0_7 <- rnorm(400,mean=10,sd=6)
attr_0_8 <- rpois(400,2)
attr_0_9 <- sample( LETTERS[1:4], 400, replace=TRUE, prob=c(0.1, 0.2, 0.65, 0.05))
attr_0_10 <- rgamma(400,shape=1)
class <- rep.int(0,400)
class0Data <- cbind(attr_0_1,attr_0_2,attr_0_3,attr_0_4,attr_0_5,attr_0_6,attr_0_7,attr_0_8,attr_0_9,attr_0_10,class)

attr_0_1 <- runif(600,min=3,max=8)
attr_0_2 <- runif(600,min=4,max=13)
attr_0_3 <- runif(600,min=14,max=46)
attr_0_4 <- runif(600,min=1,max=48)
attr_0_5 <- rnorm(600,mean=1,sd=3)
attr_0_6 <- rnorm(600,mean=15,sd=5)
attr_0_7 <- rnorm(600,mean=13,sd=9)
attr_0_8 <- rpois(600,2.5)
attr_0_9 <- sample( LETTERS[1:4], 600, replace=TRUE, prob=c(0.2, 0.3, 0.25, 0.25))
attr_0_10 <- rgamma(600,shape=2)
class <- rep.int(1,600)
class1Data <- cbind(attr_0_1,attr_0_2,attr_0_3,attr_0_4,attr_0_5,attr_0_6,attr_0_7,attr_0_8,attr_0_9,attr_0_10,class)

artificialData <- rbind(class0Data,class1Data)


artificialData <- artificialData[sample(nrow(artificialData)),]

write.csv(artificialData,'D:\\Experiment\\exp5\\Artificial-Data.csv',row.names=FALSE)

