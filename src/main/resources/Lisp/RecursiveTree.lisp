(defparameter *manual*
    '(troubleshooting_guide
        (power_issues
            (outlet_issue "Issue with outlet detected: Try a different wall outlet.")
            (power_adapter_dead "Your power adapter is dead: Please replace it with a new unit.")
            (cable_disconnected "The cable may be disconnected: Ensure the power cable is firmly plugged in."))
        (network_issues
            (isp_connection_issue "No internet signal detected: Restart router, if not resolved - call ISP."))
        (printer_hardware_issues
            (printer_tray_overload "Printer tray is overloaded: Remove the excess and restart.")
            (printer_drivers_issue "Printer drivers issue: Restart, if not resolved - contact IT.")
            (printer_head_dirty "Printer head is dirty: Run head cleaning program.")
        )
    )
)

(defun find_solution (issues_tree error_code)
    (cond
        ((null issues_tree) nil) 
        ((atom issues_tree) nil) 
        ((and (symbolp (car issues_tree)) (string-equal (symbol-name (car issues_tree)) error_code)) (cadr issues_tree))
        (t
            (let ((found_in_branch (find_solution (car issues_tree) error_code)))
            (if found_in_branch 
                found_in_branch
                (find_solution (cdr issues_tree) error_code)
                )
            )
        )
    )
)

(defun find_and_print (error-code)
  (let ((result (find_solution *manual* error-code)))
    (if result
        (format t "RESULT:~a~%" result)
        (format t "RESULT:Error code not found: ~a~%" error-code))
    (finish-output)))



#|(let ((args sb-ext:*posix-argv*))

    (if (> (length args) 2)

        (let* 
            ((error-code (car (last args)))
            (result (find_solution *manual* error-code)))

            (if result
                (format t "RESULT: ~a~%" result)
                (format t "RESULT: Error code not found.~%")
            )
        )

        (format t "RESULT:No error code provided.~%")
    )
)
|#