dataGenerator <- function(){
  library(MASS)
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
    choice = NULL
    lowRange <- readline(prompt = paste(" lower range for mean of attribute : "))
    upperRange <- readline(prompt = paste(" upper range for mean of attribute : "))
    lowRange <- as.numeric(lowRange)
    upperRange <- as.numeric(upperRange)
    for(p in 1:numberOfAttribute){
      
     
      
      choice[p] <- runif(1,min=lowRange,max=upperRange)
      choice[p] <- as.numeric(choice[p])
    }
    choice <- as.numeric(choice)
    cov <- NULL;
    lowRange <- readline(prompt = paste(" lower range for covariance of attribute : "))
    upperRange <- readline(prompt = paste(" upper range for covariance of attribute : "))
    lowRange <- as.numeric(lowRange)
    upperRange <- as.numeric(upperRange)
    for(p in 1:numberOfAttribute){
      for(j in 1:numberOfAttribute){
        cov[(p-1)*numberOfAttribute+j] <- runif(1,min=lowRange,max=upperRange)
        if(is.na( cov[(p-1)*numberOfAttribute+j])){ 
          cov[(p-1)*numberOfAttribute+j] <- 1
        }
      }
      cov <- as.numeric(cov)
    }
    
    inputMat <- matrix(cov,nrow=numberOfAttribute,ncol=numberOfAttribute,byrow=TRUE)
    tansposeMat <- t(inputMat)
    
    covMat <- inputMat %*%  tansposeMat
    
    mat <- mvrnorm(n=reduced,choice,covMat)
    
    class <- rep.int((i-1),reduced)
    mat <- cbind(mat,class,deparse.level = 0)
    
    data <- rbind(data,mat)
  }
  data <- data[sample(nrow(data)),]
  write.csv(data,paste('D:\\Experiment\\artificialData\\',fileName,sep=""),row.names=FALSE)
  cat(paste('Done Printing at : D:\\Experiment\\artificialData\\',fileName))
  
}