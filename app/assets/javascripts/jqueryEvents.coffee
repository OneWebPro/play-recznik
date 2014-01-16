define ["jquery-1.9.0.min"], ($ = jQuery) ->
#  $(document).on "keypress", "#polish-translate", (event)->
#    if event.keyCode?
#      code = event.keyCode
#    else
#      code = event.which
#    if(code == 40)
#      if !$('#polish-hints ul li:first button').is(":focus")
#        $('#polish-hints ul li:first button').focus()
#
#  $(document).on "keypress", "#serbian-translate", (event) ->
#    if event.keyCode?
#      code = event.keyCode
#    else
#      code = event.which
#    if(code == 40)
#      if $('#serbian-hints ul li').length?
#        if !$('#serbian-hints ul li:first button').is(":focus")
#          $('#serbian-hints ul li:first').focus()
#        event.preventDefault
#        return false


  $("body").on "click", ".letters .btn", (event) ->
    event.stopPropagation()
    $this = $(event.target)
    input = $(".keybord-focus")
    input.val(input.val() + $this.text())
    input.trigger('input')
    input.trigger('focus')
    false

  $("body").on "focus", "input,textarea", (event)->
    $this = $(event.target)
    $(".keybord-focus").removeClass("keybord-focus")
    $this.addClass("keybord-focus")

  offset = 220;
  duration = 500;

  $(window).scroll ->
    if $(@).scrollTop() > offset
      $('.back-to-top').fadeIn duration
    else
      $('.back-to-top').fadeOut duration


  $('.back-to-top').click (event) ->
    event.preventDefault()
    $('html, body').animate({scrollTop: 0}, duration)
    false