valid([]).
valid([Head|Tail]) :-
	fd_all_different(Head),
	valid(Tail).

sudoku(Puzzle, Solution) :-
	Solution = Puzzle,
	Puzzle =[E11, E12, E13, E14, E15, E16, E17, E18, E19,
			E21, E22, E23, E24, E25, E26, E27, E28, E29,
			E31, E32, E33, E34, E35, E36, E37, E38, E39,
			E41, E42, E43, E44, E45, E46, E47, E48, E49,
			E51, E52, E53, E54, E55, E56, E57, E58, E59,
			E61, E62, E63, E64, E65, E66, E67, E68, E69,
			E71, E72, E73, E74, E75, E76, E77, E78, E79,
			E81, E82, E83, E84, E85, E86, E87, E88, E89,
			E91, E92, E93, E94, E95, E96, E97, E98, E99],
			
	fd_domain(Solution, 1, 9),
	
	Row1 = [E11, E12, E13, E14, E15, E16, E17, E18, E19],
	Row2 = [E21, E22, E23, E24, E25, E26, E27, E28, E29],
	Row3 = [E31, E32, E33, E34, E35, E36, E37, E38, E39],
	Row4 = [E41, E42, E43, E44, E45, E46, E47, E48, E49],
	Row5 = [E51, E52, E53, E54, E55, E56, E57, E58, E59],
	Row6 = [E61, E62, E63, E64, E65, E66, E67, E68, E69],
	Row7 = [E71, E72, E73, E74, E75, E76, E77, E78, E79],
	Row8 = [E81, E82, E83, E84, E85, E86, E87, E88, E89],
	Row9 = [E91, E92, E93, E94, E95, E96, E97, E98, E99],
	
	Col1 = [E11, E21, E31, E41, E51, E61, E71, E81, E91],
	Col2 = [E12, E22, E32, E42, E52, E62, E72, E82, E92],
	Col3 = [E13, E23, E33, E43, E53, E63, E73, E83, E93],
	Col4 = [E14, E24, E34, E44, E54, E64, E74, E84, E94],
	Col5 = [E15, E25, E35, E45, E55, E65, E75, E85, E95],
	Col6 = [E16, E26, E36, E46, E56, E66, E76, E86, E96],
	Col7 = [E17, E27, E37, E47, E57, E67, E77, E87, E97],
	Col8 = [E18, E28, E38, E48, E58, E68, E78, E88, E98],
	Col9 = [E19, E29, E39, E49, E59, E69, E79, E89, E99],
	
	Square1 = [E11, E21, E31, E12, E22, E32, E13, E23, E33],
	Square2 = [E41, E51, E61, E42, E52, E62, E43, E53, E63],
	Square3 = [E71, E81, E91, E72, E82, E92, E73, E83, E93],
	Square4 = [E14, E24, E34, E15, E25, E35, E16, E26, E36],
	Square5 = [E44, E54, E64, E45, E55, E65, E46, E56, E66],
	Square6 = [E74, E84, E94, E75, E85, E95, E76, E86, E96],
	Square7 = [E17, E27, E37, E18, E28, E38, E19, E29, E39],
	Square8 = [E47, E57, E67, E48, E58, E68, E49, E59, E69],
	Square9 = [E77, E87, E97, E78, E88, E98, E79, E89, E99],
	
	valid([Row1, Row2, Row3, Row4, Row5, Row6, Row7, Row8, Row9,
		Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9,
		Square1, Square2, Square3, Square4, Square5, Square6, Square7, Square8, Square9]).