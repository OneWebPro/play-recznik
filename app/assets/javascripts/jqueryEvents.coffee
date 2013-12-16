define ["jquery-1.9.0.min"], ($ = jQuery) ->
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