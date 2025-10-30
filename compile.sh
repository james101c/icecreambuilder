#!/bin/bash
# Compile the Ice Cream Builder application

echo "Compiling Ice Cream Builder..."
mkdir -p bin
javac -d bin src/main/java/com/icecream/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "To run the application, use: java -cp bin com.icecream.IceCreamBuilder"
else
    echo "Compilation failed!"
    exit 1
fi
