
:- dynamic(status/2).


fault(power_adapter_dead) :-
    status(led_power, off),
    status(cable_plugged, yes),
    status(outlet_works, yes).

fault(cable_disconnected) :-
    status(led_power, off),
    status(cable_plugged, no).


fault(isp_connection_issue) :-
    status(led_internet, red),
    status(led_power, on).

fault(printer_tray_overload) :-
    status(led_internet, green),
    status(led_power, on),
    status(cable_plugged, yes),
    status(outlet_works, yes),
    status(paper_jammed, yes).
fault(printer_drivers_issue) :-
    status(led_internet, green),
    status(led_power, on),
    status(cable_plugged, yes),
    status(outlet_works, yes),
    status(paper_jammed, no),
    status(printed_image_streaks, no).

fault(printer_head_dirty) :-
    status(led_internet, green),
    status(led_power, on),
    status(cable_plugged, yes),
    status(outlet_works, yes),
    status(printed_image_streaks, yes).



run_diagnose :-
    (fault(Cause) ->
        format('RESULT:~w', [Cause])
    ;
        write('RESULT:unknown')
    ),
    nl,
    flush_output,
    halt.



/*
diagnose :-
    write('Unknown issue. Please contact a technician.'), nl.


report(power_adapter_dead) :-
    write('Your power adapter is likely dead. Replace it.'), nl.

report(cable_disconnected) :-
    write('Please plug in the power cable.'), nl.

report(isp_connection_issue) :-
    write('Internet signal missing. Restart router, if not resolved - call ISP.'), nl.

report(printer_tray_overload) :-
    write('Printer tray is overloaded, remove the excess and restart the cycle.'), nl.

report(printer_drivers_issue) :-
    write('Printer drivers issue, restart, if not resolved - contact IT'), nl.

report(printer_head_dirty) :-
    write('Printer head is dirty, run head cleaning program'), nl.

*/

