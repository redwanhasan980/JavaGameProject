using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
public class IconHandler : MonoBehaviour
{
    [SerializeField] private Image[] icon;
    [SerializeField] private Color UserColor;
    public void UseShot(int shotNumber)
    {
        for (int i = 0; i < icon.Length; i++)
        {
            if(shotNumber == i + 1)
            {
                icon[i].color = UserColor;
                return;
            }
        }
    }
}
