basicInput <- c(0.181818182,0.454545455,0.727272727,0.886363636,1,1,1)
probabilities <- c(0.076923077,0.294871795,0.58041958,0.823776224,0.956487956,0.995920746,1)
experimentInt <- basicInput*44
dataset <- 44
x<-list()
for(i in 1:length(basicInput)){
 k<- binom.test(experimentInt[i],dataset,p=probabilities[i],alternative='greater')
 x <- c(x,k$p.value)
}
output <- vector(length=length(basicInput))
for(i in x)
{
  k <- as.numeric(i)
  print(k)
}
for(i in x)
{
  k <- 1-as.numeric(i)
  print(k)
}

