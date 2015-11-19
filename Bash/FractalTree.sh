#!/bin/sh
#Program prints a Fractal Tree (or Bifurcation) Diagram for a desired number of repetitions (from 1 to 5)

echo "Desired number of repetitions (1 to 5): "
read beta #Desired number of bifurcations

if ! [ "$beta" -eq "$beta" ]; then
	echo "Input value not a number, set to max value..."
elif (($beta>5)); then
	echo "Input number too high, set to max value..."
	beta=5
elif (($beta<1)); then
	echo "Input number too low, set to min value..."
	beta=1
fi

delta=$(((2**(6-$beta)))) #Useful expression
currow=1 #Current row being printed

#Print as many empty lines as appropriate
for ((i=1; i<$delta; i++))
do
    for j in {1..99}
    do
        echo -n "_"
    done
    echo "_"
    currow=$(($i+1))
done

#Iterate backwards through each set of bifurcations
for (($beta; $beta>0;beta=$beta-1))
do
    startrow=$(($currow-1)) #Row at which the current set of bifurcations began
    currow=$(($startrow))
    delta=$((2**(6-$beta))) #Update delta expression for new value of beta
	echo "$currrow"
	#Print angles part of this set of bifurcations
    for ((currow; currow<($startrow+($delta/2)); currow++))
    do
		#Print leading '_', the number to print is identical for all N or beta
        for ((leading=1;leading<19;leading++))
        do
            echo -n "_"
		done
		#Print the fork for each repetition in the current set of bifurcations
        for ((repetition=0;repetition<(2**($beta-1));repetition++))
        do
            rowdiff=$(($currow-$startrow))
			echo $currrow
            for ((j=1; j<(($delta/2)+$rowdiff); j++))
            do
                echo -n "_"
			done
            echo -n "1"
            for ((k=1; k<($delta-2*$rowdiff); k++))
            do
                echo -n "_"
            done
            echo -n "1"
            for ((l=1; l<(($delta/2)+$rowdiff); l++))
            do
                echo -n "_"
            done
            echo -n "_"
        done
		
        #Print trailing '_', the number to print is identical for all N or beta
        for ((tailing=1;tailing<18;tailing++))
        do
            echo -n "_"
        done
        echo "_"
    done
    
    startrow=$currow
    currow=$(($startrow+1))
    for ((currow; currow<=($startrow+($delta/2)); currow++))
    do
		#Print leading '_', the number to print is identical for all N or beta
        for ((leading=1;leading<19;leading++))
        do
            echo -n "_"
        done
		
        #Print each repetition of the'stalk' for the set of bifurcations 
        for ((repetition=0;repetition<(2**($beta-1));repetition++))
        do
            for ((j=1; j<($delta); j++))
            do
                echo -n "_"
            done
            echo -n "1"
            for ((j=1; j<($delta); j++))
            do
                echo -n "_"
            done
            echo -n "_"
        done
		
        #Print trailing '_', the number to print is identical for all N or beta
        for ((tailing=1;tailing<18;tailing++))
        do
            echo -n "_"
        done
        echo "_"
    done
    
done