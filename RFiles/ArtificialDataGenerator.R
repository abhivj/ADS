distribution <- function(choice,numberOfInstances){
    cat('\n')
    if(choice==1){
      cat("Normal Distribution ")
      mean <- readline(prompt = "Enter Mean: ")
      mean <- as.numeric(mean)
      sd <- readline(prompt = "Enter std dev: ")
      sd <- as.numeric(sd)
      dist <- rnorm(numberOfInstances, mean = mean,sd=sd)
      return(dist)
  }
    if(choice==2){
      cat("Uniform Distribution ")
      min <- readline(prompt = "Enter Minimum Value: ")
      min <- as.numeric(min)
      max <- readline(prompt = "Enter Maximum Value: ")
      max <- as.numeric(max)
      dist <- runif(numberOfInstances,min=min,max=max)
      return(dist)
    }
  if(choice==3){
    cat("Poission Distribution ")
    lambda <- readline(prompt = "Enter lambda Value: ")
    lambda <- as.numeric(lambda)
    dist <- rpois(numberOfInstances, lambda)
    return(dist)
  }
  if(choice==4){
    cat("t-Distribution ")
    df <- readline(prompt = "degrees of freedom (> 0, maybe non-integer). df = Inf is allowed. : ")
    df <- as.numeric(df)
    ncp <- readline(prompt = "non-centrality parameter delta; currently except for rt(), only for abs(ncp) <= 37.62. : ")
    ncp <- as.numeric(ncp)
    dist <- rt(numberOfInstances, df, ncp)
    return(dist)
  }
  if(choice==5){ 
    cat("F Distribution ")
    df1 <- readline(prompt = "degrees of freedom 1 (> 0, maybe non-integer). df = Inf is allowed. : ")
    df1 <- as.numeric(df1)
    df2 <- readline(prompt = "degrees of freedom 2 (> 0, maybe non-integer). df = Inf is allowed. : ")
    df2 <- as.numeric(df2)
    ncp <- readline(prompt = "non-centrality parameter. If omitted the central F is assumed : ")
    ncp <- as.numeric(ncp)
    dist <- rf(numberOfInstances, df1, df2, ncp)
    return(dist)
  }
  if(choice==6){
    cat("Chi-Squared Distribution ")
    df <- readline(prompt = "degrees of freedom ")
    df <- as.numeric(df)
    ncp <- readline(prompt = "non-centrality parameter ")
    ncp <- as.numeric(ncp)
    dist <- rchisq(numberOfInstances, df, ncp = ncp)
    return(dist)
  }
  if(choice==7){
    cat("log-Normal Distribution ")
    meanlog <- readline(prompt = "mean of the distribution on the log scale : ")
    meanlog <- as.numeric(meanlog)
    sdlog <- readline(prompt = "standard deviation of the distribution on the log scale : ")
    sdlog <- as.numeric(sdlog)
    dist <- rlnorm(numberOfInstances, meanlog = meanlog, sdlog = sdlog)
    return(dist)
  }
  if(choice==8){
    cat("Binomial Distribution ")
    size <- readline(prompt = " number of observations per experiment : ")
    size <- as.numeric(size)
    prob <- readline(prompt = " probability of success : ")
    prob <- as.numeric(prob)
    dist <- rbinom(numberOfInstances,size,prob)
    return(dist)
  }
  if(choice==9){
    cat("HyperGeometric Distribution ")
    m <- readline(prompt = "the number of white balls in the urn : ")
    m <- as.numeric(m)
    n <- readline(prompt = "the number of black balls in the urn : ")
    n <- as.numeric(n)
    k <- readline(prompt = "the number of balls drawn from the urn : ")
    k <- as.numeric(k)
    dist <- rhyper(numberOfInstances, m, n, k)
    return(dist)
  }
  if(choice==10){
    cat("Beta Distribution")
    shape1 <- readline(prompt = "parameter of Beta distribution shape 1  ")
    shape1 <- as.numeric(shape1)
    shape2 <- readline(prompt = "parameter of Beta distribution shape 2  ")
    shape2 <- as.numeric(shape2) 
    ncp <- readline(prompt = "non-centrality parameter ")
    ncp <- as.numeric(ncp)
    dist <- rbeta(numberOfInstances, shape1, shape2, ncp)
    return(dist)
  }
  if(choice==11){
    cat("Gamma Distribution")
    rate <- readline(prompt = "rate of Gamma Distribution ")
    rate <- as.numeric(rate) 
    shape <- readline(prompt = "shape of Gamma Distribution ")
    shape <- as.numeric(shape)
    dist <- rgamma(numberOfInstances, shape, rate, scale = 1/rate)
    return(dist)
  }
  if(choice==12){
    cat("Weibull Distribution")
    shape <- readline(prompt = "shape of Weibull Distribution : ")
    shape <- as.numeric(shape)
    scale <- readline(prompt = "scale of Weibull Distribution : ")
    scale <- as.numeric(scale)
    dist <- rweibull(numberOfInstances, shape, scale)
    return(dist)
  }
  if(choice==13){
    cat("Categorical Variable")
    numberOfCategory <- readline(prompt = "number of nominal Values : ")
    numberOfCategory <- as.numeric(numberOfCategory)
    prob <- NULL
    reduced <- 0
    total <- numberOfInstances
    sumOfProb <- 0
    for(i in 1:(numberOfCategory-1)){
      total <- total - reduced
      reduced <- readline(prompt = paste("Enter no of Instances of category (",as.character(i),") out of ",as.character(numberOfInstances)," remaining ",as.character(total),' : '))
      reduced <- as.numeric(reduced)
      prob[i] <- reduced/numberOfInstances
      sumOfProb <- sumOfProb + prob[i]
    }
    prob[numberOfCategory] <- 1-sumOfProb
    dist <- sample( LETTERS[1:numberOfCategory], numberOfInstances, replace=TRUE, prob=prob)
    return(dist)
  }
  
}






dataGenerator <- function(){
  fileName <- readline(prompt = "Enter File Name: ")
  numberOfRecords <- readline(prompt = "Enter No of Instances: ")
  numberOfRecords <- as.numeric(numberOfRecords)
  if(is.na(numberOfRecords)){ 
    stop("The value provided is not valid. Please provide a number.")}
  
  numberOfClass <- readline(prompt = "Enter No of classes: ")
  numberOfClass <- as.numeric(numberOfClass)
  if(is.na(numberOfClass)){ 
    stop("The value provided is not valid. Please provide a number.")}
    
  numberOfAttribute <- readline(prompt = "Enter No of attribute: ")
  numberOfAttribute <- as.numeric(numberOfAttribute)
  if(is.na(numberOfAttribute)){ 
    stop("The value provided is not valid. Please provide a number.")}
  choice = NULL
  for(i in 1:numberOfAttribute){
    
   
   cat("Enter 1 for Normal Distribution
Enter 2 for uniform Distribution
Enter 3 for Poission Distribution
Enter 4 for t Distribution
Enter 5 for F Distribution
Enter 6 for Chi-Squared Distribution
Enter 7 for log-Normal Distribution
Enter 8 for Binomial Distribution 
Enter 9 for HyperGeometric Distribution
Enter 10 for Beta Distribution
Enter 11 for Gamma Distribution
Enter 12 for Weibull Distribution
Enter 13 for Categorical Data") 
   
   choice[i] <- readline(prompt = paste("Choice for attribute (",as.character(i),") : "))
   choice[i] <- as.numeric(choice[i])
  }
  prob <- NULL
  reduced <- 0
  total <- numberOfRecords
  sumOfProb <- 0
  data <- NULL
  for(i in 1:numberOfClass){
    if(i!=numberOfClass){
      reduced <- readline(prompt = paste("Enter no of Instances of class (",as.character(i),") out of ",as.character(numberOfRecords)," remaining ",as.character(total),' : '))
      reduced <- as.numeric(reduced) 
      total <- total - reduced
    }
    else{
      reduced <- total
    }
    mat <- NULL
    cat(paste('Enter data for class ',as.numeric(i)))
    for(j in 1:numberOfAttribute){
      dist <- distribution(choice[j],reduced)
      mat <- cbind(mat,dist,deparse.level = 0)
    }
    class <- rep.int((i-1),reduced)
    mat <- cbind(mat,class,deparse.level = 0)
    
    data <- rbind(data,mat)
  }
  data <- data[sample(nrow(data)),]
  write.csv(data,paste('D:\\Experiment\\exp5\\',fileName),row.names=FALSE)
  cat(paste('Done Printing at : D:\\Experiment\\exp5\\',fileName))
  
}


