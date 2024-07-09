using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem;
public class SlingshotArea : MonoBehaviour
{  [SerializeField] private LayerMask Areamask;
   public bool IsInsideSlingShotArea()
   {
     Vector2 woldPosition = Camera.main.ScreenToWorldPoint(InputManager.MousePosition);
     if(Physics2D.OverlapPoint(woldPosition,Areamask))
     {
         return true;
     }
     return false;
   }
}
