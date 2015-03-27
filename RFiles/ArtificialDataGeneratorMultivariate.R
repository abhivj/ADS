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
    for(i in 1:numberOfAttribute){
      
      choice[i] <- readline(prompt = paste(" mean of attribute (",as.character(i),") : "))
      choice[i] <- as.numeric(choice[i])
    }
    choice <- as.numeric(choice)
    cov <- NULL;
    for(i in 1:numberOfAttribute){
      for(j in 1:numberOfAttribute){
        cov[(i-1)*numberOfAttribute+j] <- readline(prompt = paste(" covariance matrix entry row (",as.character(i),") coloumn (",as.character(j),") : "))
      }
      cov <- as.numeric(cov)
    }
    
    covMat <- matrix(cov,nrow=numberOfAttribute,ncol=numberOfAttribute,byrow=TRUE)
    mat <- mvnnorm(n=reduced,choice,covMat)
    
    class <- rep.int((i-1),reduced)
    mat <- cbind(mat,class,deparse.level = 0)
    
    data <- rbind(data,mat)
  }
  data <- data[sample(nrow(data)),]
  write.csv(data,paste('D:\\Experiment\\exp5\\',fileName),row.names=FALSE)
  cat(paste('Done Printing at : D:\\Experiment\\exp5\\',fileName))
  
}