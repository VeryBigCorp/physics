CFLAGS = -ggdb -std=c++11 -pthread -Wl,--no-as-needed
DEFINES = -DDEBUGGA
INCLUDES = ../lib/Vector.cpp
LIBS = -lstdc++
OUTPUT = drag.o
CC=g++

.cpp.o:	$(INCLUDES)
	$(CC) $(CFLAGS) -c $(DEFINES) $(INCLUDES) $<

all::	drag

gnuplot_i.o:	../lib/gnuplot_i.hpp
Vector.o: 	../lib/Vector.cpp
drag.o:		drag.cpp

drag: 	$(OUTPUT)
	$(CC) $(CFLAGS) -o $@  $(OUTPUT) $(LIBS)

clean: 
	rm -f $(OUTPUT) drag
