import { useState } from 'react';
import './Footer.scss'

function Footer() {
  const [buttonActiveIndex, setButtonActiveIndex] = useState(0)
  const menus = [
    {
      name: 'home',
      basic: '/asset/nav/home.svg',
      active: '/asset/nav/home_active.svg'
    },
    {
      name: 'setting',
      basic: '/asset/nav/user.svg',
      active: '/asset/nav/setting_active.svg'
    },
    {
      name: 'setting',
      basic: '/asset/nav/setting.svg',
      active: '/asset/nav/setting_active.svg'
    }
  ]

  const toggleActive = index => {
    setButtonActiveIndex(() => index)
  }
  
  return (
    <div className='footer-root-container'>
      <div className='footer-nav'>
        {/* {
          menus.map((menu, index) => (
            <div className='button' key={index}>
              <img 
                src={buttonActiveIndex === index ? menu.active : menu.basic} 
                alt={menu.name}
                onClick={() => toggleActive(index)} 
                />
            </div>
          ))
        } */}
          <div className='button' onClick={() => toggleActive(0)}>
              <img src={buttonActiveIndex === 0 ? menus[0].active : menus[0].basic} alt='home'></img>
          </div>
          <div className='button' onClick={() => toggleActive(1)}>
              <img src={buttonActiveIndex === 1 ? menus[1].active : menus[1].basic} alt='user'></img>
          </div>
          <div className='button' onClick={() => toggleActive(2)}>
              <img src={buttonActiveIndex === 2 ? menus[2].active : menus[2].basic} alt='setting'></img>
          </div>
      </div>
    </div>
  );
}
  
export default Footer