distribution <- function(numberOfInstances){

 
  
    mean <-  runif(1,min=1,max=5)
    mean <- as.numeric(mean)
    sd <- runif(1,min=1,max=5)
    sd <- as.numeric(sd)
    dist <- rnorm(numberOfInstances, mean = mean,sd=sd)
    return(dist)
  


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
        dist <- distribution(reduced)
        mat <- cbind(mat,dist,deparse.level = 0)
      }
      class <- rep.int((i-1),reduced)
      mat <- cbind(mat,class,deparse.level = 0)
      
      data <- rbind(data,mat)
    }
    data <- data[sample(nrow(data)),]
    write.csv(data,paste('D:\\Experiment\\large\\dataset\\UnivariateDatasets\\',fileName,sep=""),row.names=FALSE)
    cat(paste('Done Printing at : D:\\Experiment\\large\\dataset\\UnivariateDatasets\\',fileName))
  }
}
