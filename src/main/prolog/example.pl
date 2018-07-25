%'Person'('Mario', 39).
%'Person'('Mark', 40).
%
%'Person#name'(X) :-
%    'Person'(X, _).
%
%'Person#age'(Y) :-
%    'Person'(_, Y).
%

'Person'([name(X), age(Y)], person(name(X), age(Y))).
'Person'([name('Mario'), age(39)], X).
'Person'([name('Mark'), age(40)], X).

person([name(X), age(Y)], person(name(X), age(Y))).
person(name('Mario'), age(39)).
person(name('Mark'), age(40)).

person(person(name(X), age(Y))) :-
    person(name(X), age(Y)).


?- person(X), X=person(_, age(Y)), Y > 30.


person([name('Mario'), age(39)]).
person([name('Mark'), age(40)]).

exists(person(name(X), age(Y))) :-
    person(name(X), age(Y)).