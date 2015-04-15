basicInput <- c(
  0,
  0,
  0.008264463,
  0.068181818,
  0.150826446,
  0.268595041,
  0.400826446,
  0.541322314,
  0.683884298,
  0.780991736,
  0.863636364,
  0.919421488,
  0.960743802,
  0.975206612,
  0.981404959
  
  
  
  )
probabilities <- c(
  0.014285714,
  0.056728778,
  0.124862989,
  0.213955796,
  0.317534624,
  0.428182857,
  0.538469854,
  0.641846095,
  0.733341187,
  0.809949161,
  0.870659846,
  0.916175987,
  0.948419826,
  0.969963161,
  0.983506557
  
  
  )
experimentInt <- basicInput*484
dataset <- 484
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

