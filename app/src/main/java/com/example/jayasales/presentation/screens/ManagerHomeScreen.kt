package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerHomeScreen(
    notifier: NotificationService = rememberNotifier(),
    openLogoutDialog: Boolean = boolState(key = MyDataIds.managerOpendialog).value,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(0.dep),
                modifier = Modifier
                    .padding(end = 68.dep)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(28.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dep)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier
                                .size(
                                    width = 64.dep,
                                    height = 64.dep
                                )
                                .shadow(2.dep, shape = CircleShape)
                        )
                        Card(
                            colors = CardDefaults.cardColors(Color.White),
                            shape = CircleShape,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(20.dep),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep
                            )
                        ) {
                            IconButton(
                                onClick = {
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(5.dep)
                                        .size(width = 12.dep, height = 12.dep),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        Text(
                            text = "nameState.value",
                            fontSize = 18.sep,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.height(2.dep))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "",
                                tint = Color(0xFFD62B2B),
                                modifier = Modifier.size(width = 16.dep, height = 20.dep)
                            )
                            Text(
                                text = "emailState.value",
                                fontSize = 12.sep,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dep))
                Divider(
                    modifier = Modifier
                        .height(0.3.dep)
                        .padding(horizontal = 20.dep)
                        .background(color = Color.LightGray)
                )
                Spacer(modifier = Modifier.height(20.dep))
                /* Row(
                     verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                         .height(40.dep)
                         .fillMaxWidth()
                         .clickable {
                             notifier.notify(MyDataIds.returnRequest)
                         }
                 ) {
                     Icon(
                         painterResource(id = R.drawable.returnrequest),
                         contentDescription = "",
                         modifier = Modifier
                             .padding(start = 16.dp)
                     )
                     Spacer(Modifier.width(8.dep))
                     Text(
                         text = stringResource(id = R.string.Return),
                         fontSize = 14.sep,
                         color = Color(0xFF222222)
                     )
                 }*/
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(40.dep)
                        .fillMaxWidth()
                        .clickable {
                            notifier.notify(MyDataIds.managerLogout)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    Spacer(Modifier.width(8.dep))
                    Text(
                        text = stringResource(id = R.string.Logout),
                        fontSize = 14.sep,
                        color = Color(0xFF222222)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        val openDialog = remember { mutableStateOf(false) }
        if (openLogoutDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Login",
                    )
                },
                text = {
                    Text("Are you sure to logout")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            notifier.notify(MyDataIds.managerConfirm)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFB81B1B))
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            notifier.notify(MyDataIds.managerDismiss)
                        }) {
                        Text("Dismiss")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "",
                        tint = Color(0xFFB81B1B),
                        modifier = Modifier
                            .size(24.dep)
                    )
                }
            )
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .shadow(2.dep),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.jayasales),
                            contentDescription = stringResource(id = R.string.logo),
                            modifier = Modifier
                                .height(60.dp)
                                .width(100.dp),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier
                                .height(20.dep)
                                .width(32.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.HamBurger),
                                tint = Color(0xFF191919)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color(0xFFFFEB56)),
                    actions = {
                        IconButton(
                            onClick = {
                                // notifier.notify(MyDataIds.notification)
                            },
                            modifier = Modifier
                                .height(36.dep)
                                .width(40.dep)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = stringResource(id = R.string.notification),
                                tint = Color(0xFF191919)
                            )
                        }
                    }
                )
            },
        )
        {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 20.dep)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color(0xFFD62B2B), RoundedCornerShape(4.dep))
                            .height(112.dep)
                            .weight(.5f)
                            .clip(RoundedCornerShape(4.dep))
                            .clickable { notifier.notify(MyDataIds.order) }
                            .padding(horizontal = 12.dep)
                            .padding(vertical = 12.dep)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Order",
                                fontSize = 16.sep,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier
                            )
                            IconButton(
                                onClick = {
                                    notifier.notify(MyDataIds.order)
                                },
                                modifier = Modifier
                                    .background(Color.White, CircleShape)
                                    .size(20.dep)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForwardIos,
                                    contentDescription = "ArrowBackIosNew",
                                    tint = Color(0xFFD62B2B),
                                    modifier = Modifier
                                        .size(12.dep)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.order),
                                contentDescription = "ArrowBackIosNew",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(40.dep)
                            )
                            Text(
                                text = "30",
                                fontSize = 32.sep,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dep))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color(0xFF2DB87C), RoundedCornerShape(4.dep))
                            .height(112.dep)
                            .weight(.5f)
                            .padding(horizontal = 12.dep)
                            .padding(vertical = 12.dep)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sales Men",
                                fontSize = 16.sep,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier
                            )
                            IconButton(
                                onClick = {
                                },
                                modifier = Modifier
                                    .background(Color.White, CircleShape)
                                    .size(20.dep)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForwardIos,
                                    contentDescription = "ArrowBackIosNew",
                                    tint = Color(0xFFD62B2B),
                                    modifier = Modifier
                                        .size(12.dep)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.group),
                                contentDescription = "ArrowBackIosNew",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(40.dep)
                            )
                            Text(
                                text = "20",
                                fontSize = 32.sep,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Distributor order",
                        fontSize = 17.sep,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier
                    )
                    TextButton(onClick = {

                    }) {
                        Text(
                            text = "View All",
                            fontSize = 13.sep,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFD62B2B),
                            modifier = Modifier
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dep))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    items(count = 2) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    4.dep,
                                    RoundedCornerShape(4.dep),
                                    clip = true,
                                    DefaultShadowColor
                                )
                                .clip(RoundedCornerShape(4.dep))
                                .clickable {
                                },
                            colors = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep,
                                focusedElevation = 10.dep,
                            ),
                            shape = RoundedCornerShape(4.dep),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .padding(top = 16.dep)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Order:#09782",
                                        fontSize = 13.sep,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                    Spacer(modifier = Modifier.width(6.dep))
                                    Divider(
                                        modifier = Modifier
                                            .height(22.dep)
                                            .width(1.5.dep),
                                        color = Color(0xFF707070)
                                    )
                                    Spacer(modifier = Modifier.width(6.dep))
                                    Text(
                                        text = "Pending",
                                        fontSize = 13.sep,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF247FBB),
                                        modifier = Modifier
                                    )
                                }
                                Text(
                                    text = "Items:32",
                                    fontSize = 13.sep,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dep))
                            Divider(
                                color = Color(0xFFE9E5E5)
                            )
                            Spacer(modifier = Modifier.height(12.dep))
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.house),
                                        contentDescription = stringResource(id = R.string.notification),
                                        tint = Color(0xFFD62B2B)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = "Ram Prasad Traders",
                                        fontSize = 15.sep,
                                        //fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFD62B2B),
                                        modifier = Modifier
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.date),
                                        contentDescription = stringResource(id = R.string.notification),
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(16.dep)
                                    )
                                    Spacer(modifier = Modifier.width(4.dep))
                                    Text(
                                        text = "24-04-2024",
                                        fontSize = 12.sep,
                                        //fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dep))
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = stringResource(id = R.string.notification),
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dep)
                                )
                                Spacer(modifier = Modifier.width(4.dep))
                                Text(
                                    text = "Howrah, Bally West Bengal",
                                    fontSize = 14.sep,
                                    //fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dep))
                            Button(
                                onClick = {
                                },
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .padding(bottom = 10.dep)
                                    .fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(Color(0xFF2DB87C)),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 8.dep,
                                    pressedElevation = 10.dep
                                ),
                                shape = RoundedCornerShape(4.dep)
                            ) {
                                Text(
                                    text = "Confirm Order",
                                    fontSize = 15.sep,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}