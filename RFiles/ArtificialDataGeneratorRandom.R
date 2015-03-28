distribution <- function(choice,numberOfInstances){
  cat('\n')
  if(choice==1){
    cat("Normal Distribution ")
    mean <-  runif(1,min=1,max=5)
    mean <- as.numeric(mean)
    sd <- runif(1,min=1,max=5)
    sd <- as.numeric(sd)
    dist <- rnorm(numberOfInstances, mean = mean,sd=sd)
    return(dist)
  }
  if(choice==2){
    cat("Uniform Distribution ")
    min <- runif(1,min=1,max=90)
    min <- as.numeric(min)
    max <- runif(1,min=min,max=100)
    max <- as.numeric(max)
    dist <- runif(numberOfInstances,min=min,max=max)
    return(dist)
  }
  if(choice==3){
    cat("Poission Distribution ")
    lambda <- runif(1,min=1,max=3)
    lambda <- as.numeric(lambda)
    dist <- rpois(numberOfInstances, lambda)
    return(dist)
  }
  if(choice==4){
    cat("t-Distribution ")
    df <- runif(1,min=1,max=5)
    df <- as.numeric(df)
    ncp <- runif(1,min=1,max=3)
    ncp <- as.numeric(ncp)
    dist <- rt(numberOfInstances, df, ncp)
    return(dist)
  }
  if(choice==5){ 
    cat("F Distribution ")
    df1 <- runif(1,min=1,max=5)
    df1 <- as.numeric(df1)
    df2 <- runif(1,min=1,max=5)
    df2 <- as.numeric(df2)
    ncp <- runif(1,min=1,max=5)
    ncp <- as.numeric(ncp)
    dist <- rf(numberOfInstances, df1, df2, ncp)
    return(dist)
  }
  if(choice==6){
    cat("Chi-Squared Distribution ")
    df <- runif(1,min=1,max=5)
    df <- as.numeric(df)
    ncp <- runif(1,min=1,max=3)
    ncp <- as.numeric(ncp)
    dist <- rchisq(numberOfInstances, df, ncp = ncp)
    return(dist)
  }
  if(choice==7){
    cat("log-Normal Distribution ")
    meanlog <- runif(1,min=1,max=10)
    meanlog <- as.numeric(meanlog)
    sdlog <- runif(1,min=1,max=4)
    sdlog <- as.numeric(sdlog)
    dist <- rlnorm(numberOfInstances, meanlog = meanlog, sdlog = sdlog)
    return(dist)
  }
  
  }
  if(choice==8){
    cat("HyperGeometric Distribution ")
    m <- ceiling(runif(1,min=10,max=50))
    m <- as.numeric(m)
    n <- ceiling(runif(1,min=10,max=50))
    n <- as.numeric(n)
    k <- ceiling(runif(1,min=1,max=10))
    k <- as.numeric(k)
    dist <- rhyper(numberOfInstances, m, n, k)
    return(dist)
  }
}


dataGenerator <- function(FileName,noOfFiles,instanceLower,instanceUpper,attrLower,attrUpper){
  
  for(fl in 1:noOfFiles)
  {
    
    fileName <- paste(FileName,fl,sep="")
    fileName <- paste(fileName,".csv",sep="")
    
    
    numberOfRecords <- ceiling(runif(1,min=instanceLower,max=instanceUpper))
    numberOfRecords <- as.numeric(numberOfRecords)
    if(is.na(numberOfRecords)){ 
      stop("The value provided is not valid. Please provide a number.")}
    
    numberOfClass <- 2
    numberOfClass <- as.numeric(numberOfClass)
    if(is.na(numberOfClass)){ 
      stop("The value provided is not valid. Please provide a number.")}
    
    numberOfAttribute <-ceiling(runif(1,min=attrLower,max=attrUpper))
    numberOfAttribute <- as.numeric(numberOfAttribute)
    if(is.na(numberOfAttribute)){ 
      stop("The value provided is not valid. Please provide a number.")}
    
    
  choice = NULL
  for(i in 1:numberOfAttribute){
    
    choice[i] <- ceiling(runif(1,min=1,max=9))
    choice[i] <- as.numeric(choice[i])
  }
  prob <- NULL
  reduced <- 0
  total <- numberOfRecords
  sumOfProb <- 0
  data <- NULL
  for(i in 1:numberOfClass){
    if(i!=numberOfClass){
      reduced <- ceiling(runif(1,min=1,max=numberOfRecords))
      reduced <- as.numeric(reduced) 
      total <- total - reduced
    }
    else{
      reduced <- total
    }
    mat <- NULL
   
    for(j in 1:numberOfAttribute){
      dist <- distribution(choice[j],reduced)
      mat <- cbind(mat,dist,deparse.level = 0)
    }
    class <- rep.int((i-1),reduced)
    mat <- cbind(mat,class,deparse.level = 0)
    
    data <- rbind(data,mat)
  }
  data <- data[sample(nrow(data)),]
  write.csv(data,paste('D:\\Experiment\\artificialData\\distributionData\\',fileName,sep=""),row.names=FALSE)
  cat(paste('Done Printing at : D:\\Experiment\\artificialData\\distributionData\\',fileName))
  }
}

