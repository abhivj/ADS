basicInput <- c(
  0.361983471,
  0.502479339,
  0.586914601,
  0.585950413,
  0.594958678,
  0.61584022,
  0.667945691,
  0.732696281,
  0.801974288,
  0.860702479,
  0.888617581,
  0.919524793,
  0.956071202,
  1
  
)
probabilities <- c(
  0.071428571,
  0.142857143,
  0.214285714,
  0.285714286,
  0.357142857,
  0.428571429,
  0.5,
  0.571428571,
  0.642857143,
  0.714285714,
  0.785714286,
  0.857142857,
  0.928571429,
  1
  
  
)
experimentInt <- basicInput*484
dataset <- 484
x<-list()
for(i in 1:length(basicInput)){
  step <- i*5
  #step<-1
 k<- binom.test(as.integer(experimentInt[i])*step,dataset*step,p=probabilities[i],alternative='greater')
 x <- c(x,k$p.value)
}
output <- vector(length=length(basicInput))
for(i in x)
{
  k <- as.numeric(i)
  cat(k)
  cat('\n')
}
for(i in x)
{
  k <- 1-as.numeric(i)
 # print(k)
 cat(k)
 cat('\n')
}

