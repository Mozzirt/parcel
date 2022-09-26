import './Footer.scss'

function Footer() {
  const menus = ['home', 'setting']
  const src = {
    home: '/asset/nav/home_active.svg',
    setting: '/asset/nav/setting.svg'
  }

  function test(event) {
    const menu = event.target.alt

    if(src[menu].indexOf('_active') === -1) {
      src[menu] = src[menu].replace('.svg', '_active.svg')

      const inactiveMenus = menus.filter(v => v !== menu)
      inactiveMenus.forEach(v => src[v] = src[v].replace('_active.svg', '.svg'))
    }
  }

  return (
    <div className='footer-root-container'>
      <div className='footer-nav'>
          <div className='button' onClick={test}>
              <img src={src.home} alt='home'></img>
          </div>
          <div className='button'  onClick={test}>
              <img src={src.setting} alt='setting'></img>
          </div>
      </div>
    </div>
  );
}
  
export default Footer