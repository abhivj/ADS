dataGenerator <- function(FileName,noOfFiles,instanceLower,instanceUpper,attrLower,attrUpper,meanLower,meanUpper,covLower,covUpper){
  library(MASS)
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

    choice = NULL
  

    for(p in 1:numberOfAttribute){
      
      choice[p] <- runif(1,min=meanLower,max=meanUpper)
      choice[p] <- as.numeric(choice[p])
    }
    choice <- as.numeric(choice)
    cov <- NULL;
   
    for(p in 1:numberOfAttribute){
      for(j in 1:numberOfAttribute){
        cov[(p-1)*numberOfAttribute+j] <- runif(1,min=covLower,max=covUpper)
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
  write.csv(data,paste('D:\\Experiment\\large\\dataset\\multivariateDatasets\\',fileName,sep=""),row.names=FALSE)
  cat(paste('Done Printing at : D:\\Experiment\\large\\dataset\\multivariateDatasets\\',fileName))
  }
}