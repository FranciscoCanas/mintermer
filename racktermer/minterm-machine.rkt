#lang racket

#| Given a list of a digital logic circuit's required output,
outputs the minterm boolean expressions in verilog syntax. 

Example:
In: (1 0 0 1)
Out: (~in[1] & ~in[0]) | (in[1] & in[0])

In: (0 0 1 1 0 1 1 1)
Out: 
(~in[2] & in[1] & ~in[0]) | 
(~in[2] & in[1] & in[0]) | 
(in[2] & ~in[1] & in[0]) | 
(in[2] & in[1] & ~in[0]) | 
(in[2] & in[1] & in[0])
|#

#|Name of the verilog wire or input. Change as needed.|#
(define inputname "in")

#| Log of a base b|#
(define (logb a b)
  (/ (log a) (log b))
  )

#|# of bits to represent num in binary|#
(define (bitsbinary num)
  (+ 1 (floor (logb num 2)))
  )

#|Decimal to binary|#
(define (dec-to-bin dec)
  (if (> dec 0)
      (+ 
       (modulo dec 2)
       (* 10
          (dec-to-bin 
           (floor 
            (/ dec 2)))
          )
       )
      0)
  )

#|Binary in as many bits as specified. Returns in list form.|#
(define (dec-to-bin-list dec bits)
  (if (> bits 0)
      (append            
       (dec-to-bin-list 
        (floor 
         (/ dec 2)) (- bits 1))
       (list (modulo dec 2))
       )
      '())
  )

#|Takes a list of inputs (binary) and returns a minterm in verilog code.|#
(define (bin-list-to-minterm-list blist)
  (map (λ (i n) 
         (bin-to-veristring i n))
       blist
       (reverse (sequence->list (in-range (length blist)))))
  )

#|Takes a value and input, generates a verilog string to represent it.|#
(define (bin-to-veristring value input)
  (define str 
    (string-append inputname "[" (number->string input) "]"))
  (if (eq? 0 value)
      (string-append "~" str)
      str
      )
  )

#|Returns a verilog code for some minterm in string form|#
(define (termlist-to-veristring termlist)
  (string-append "("
                 (apply string-append
                        (add-between termlist " & ")
                        )
                 ")")
  )

#|Takes a list of 1 and 0's representing a circuit output. Returns 
the minterms required to make this output as a string of verilog code|# 
(define (minterm-maker outputs)
  (define num (length outputs))
  (define bits (bitsbinary (- num 1)))
  (define binsequence (map (λ (i)
                             (dec-to-bin-list i bits))
                           (sequence->list (in-range num))))
  (define term-list (filter-map (λ (b o) 
                                  (and (equal? 1 o)
                                       (bin-list-to-minterm-list b))
                                  )
                                binsequence
                                outputs))
  (apply string-append
         (add-between (map termlist-to-veristring
                           term-list) " | "))
  )

#|Enter a list of 1's and 0's|#
(display (minterm-maker (read)))


