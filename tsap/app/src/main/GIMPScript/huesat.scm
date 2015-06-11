(define (batch-hue-saturation pattern
                              hue-range
                              hue-offset
                              lightness
                                                          saturation)
  (let* ((filelist (cadr (file-glob pattern 1))))
    (while (not (null? filelist))
           (let* ((filename (car filelist))
                  (image (car (gimp-file-load RUN-NONINTERACTIVE
                                              filename filename))))
                                  (if (not (= RGB (car (gimp-image-base-type image))))
                                                                  (gimp-image-convert-rgb image))
                  (let*((drawable (car (gimp-image-get-active-drawable image))))
                                  (gimp-hue-saturation drawable
                                   hue-range hue-offset lightness saturation)
             (gimp-file-save RUN-NONINTERACTIVE
                             image drawable filename filename)
             (gimp-image-delete image))
           (set! filelist (cdr filelist))))))
