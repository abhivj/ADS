stackedAutoEncoder <- function(a,inputFile,outputFile){
  csvData <- read.csv(inputFile,header=TRUE)
  fileNames <- as.matrix(csvData[1],header=FALSE)
  fileNames <- matrix(fileNames, ncol = ncol(fileNames), dimnames = NULL) #converting filename 
  mat <- as.matrix(csvData[2:dim(csvData)[2]]) #Excluding filenames
 
  library(autoencoder)
  nl=3 ## number of layers (default is 3: input, hidden, output)
  unit.type = "logistic" ## specify the network unit type, i.e., the unit's
  ## activation function ("logistic" or "tanh")
  N.input = dim(mat)[2] ## number of units (neurons) in the input layer (one unit per pixel)
 
  N.hidden = 100 ## number of units in the hidden layer
  lambda = 0.0002 ## weight decay parameter
  beta = 6 ## weight of sparsity penalty term
  rho = 0.01 ## desired sparsity parameter
  epsilon <- 0.001 ## a small parameter for initialization of weights
  
  ## as small gaussian random numbers sampled from N(0,epsilon^2)
  max.iterations = 2000 ## number of iterations in optimizer
  loopMatrix <- mat
  for(i in 1:length(a)){
    
    N.input = dim(loopMatrix)[2]
    N.hidden = a[i]
    autoencoder.object <- autoencode(X.train=mat,nl=nl,N.hidden=N.hidden,
                                     unit.type=unit.type,lambda=lambda,beta=beta,rho=rho,epsilon=epsilon,
                                     optim.method="BFGS",max.iterations=max.iterations,
                                     rescale.flag=FALSE,rescaling.offset=0.001)
    
    ## Extract weights W and biases b from autoencoder.object:
    W <- autoencoder.object$W
    b <- autoencoder.object$b
    
    layer<-feedforward.pass.matrix(W,b,mat)$a[[2]]
    #combines names with output matrix
    loopMatrix <- layer
    
  }
  combinedWithHidden <- cbind(fileNames,loopMatrix)
  write.csv(combinedWithHidden,outputFile,row.names=FALSE)
  
}


activation <- function(z){#activation (output) of neurons
  if (unit.type=="logistic") return(1/(1+exp(-z)))
  if (unit.type=="tanh") return(tanh(z))
}

feedforward.pass.matrix <- function(W,b,X){#performs a feedforward pass in the network, given input matrix X
  #1. Perform a feedforward pass, computing the activations for layers L2,
  #L3, and so on up to the output layer Lnl, using Eqs (6)-(7):
  NrowX = nrow(X)
  z <- list()
  a <- list()
  a[[1]] <- X  #the first element of the activations list is the input matrix, with rows i=1,...,Ntrain counting the training examples, and columns j=1,...,sl[1] counting the input channels.
  for (l in 2:nl){
    #    z[[l]] <- t(W[[l-1]] %*% t(a[[l-1]]) + matrix(b[[l-1]],nrow=length(b[[l-1]]),ncol=Ntrain))  #matrix z[[l]][k,i], rows k=1,...,Ntrain, columns i=1,...,sl[l+1]
    z[[l]] <- t(W[[l-1]] %*% t(a[[l-1]])) + matrix(b[[l-1]],nrow=NrowX,ncol=length(b[[l-1]]),byrow=TRUE)  #matrix z[[l]][k,i], rows k=1,...,Ntrain, columns i=1,...,sl[l+1]
    a[[l]] <- activation(z[[l]])  #matrix a[[l]][k,i], rows k=1,...,Ntrain, columns i=1,...,sl[l+1]
  }
  
  return(list("z"=z,"a"=a))
}
rescale <- function(X.in,X.in.min=NULL,X.in.max=NULL,unit.type,offset){ #offset is a small number to avoid having exactly 0 or 1, e.g., offset=0.01
  if (!is.numeric(X.in.min)) X.in.min <- min(X.in)  #minimum of all elements of X.in
  if (!is.numeric(X.in.max)) X.in.max <- max(X.in)  #maximum of all elements of X.in
  if (unit.type=="logistic"){#rescale X.in in a uniform way, so that all input channels are within (0,1)=[offset,1-offset]
    #shift to [0,...]:
    X.in <- X.in - X.in.min
    #rescale uniformly to fit all rows within [0,1]:
    X.in <- X.in/(X.in.max-X.in.min)
    #rescale uniformly to fit all rows withing [offset,1-offset]:
    L <- 1-2*offset
    X.in <- X.in*L + offset
  }
  if (unit.type=="tanh"){#rescale X.in so that all input channels are within (-1,1)=[-1+offset,1-offset]
    #shift to [0,...]:
    X.in <- X.in - X.in.min
    #rescale uniformly to fit all rows within [0,2]:
    X.in <- X.in/(X.in.max-X.in.min)*2
    #rescale uniformly to fit all rows within [offset,2-offset], and then shift to [-1+offset,1-offset]:
    L <- 1-offset
    X.in <- X.in*L + offset - 1
  }
  
  #Return rescaled X.in, X.in.min, X.in.max, and unit.type, for rescaling back
  return(list("X.rescaled"=X.in,"X.min"=X.in.min,"X.max"=X.in.max,"unit.type"=unit.type,"offset"=offset))
}
rescale.back <- function(X.in,X.in.min,X.in.max,unit.type,offset){#revert the scaling done by rescale()
  if (unit.type=="logistic"){
    X.in <- (X.in-offset)/(1-2*offset)*(X.in.max-X.in.min) + X.in.min
  }
  if (unit.type=="tanh"){
    X.in <- (X.in-offset+1)/(1-offset)*(X.in.max-X.in.min)/2 + X.in.min
  }
  #Return rescaled back X.in
  return(list("X.rescaled"=X.in))
}

input <- 'D:\\Experiment\\exp4\\AllAttributes.csv'
output <- 'D:\\Experiment\\exp4\\stacked-Autoencoder-c(100,90,75).csv'
neuronList <- c(100,90,75)
stackedAutoEncoder(neuronList,input,output)
