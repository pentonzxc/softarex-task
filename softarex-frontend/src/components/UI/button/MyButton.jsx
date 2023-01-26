import React from 'react'
import cl from './MyButton.module.css'

export default function MyButton(props) {
  return (
    <button className={cl.myButton} {...props}>
        {props.children}
    </button>
  )
}
