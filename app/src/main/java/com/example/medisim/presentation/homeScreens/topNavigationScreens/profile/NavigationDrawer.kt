package com.example.medisim.presentation.homeScreens.topNavigationScreens.profile

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.HighlightOff
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.medisim.R
import com.example.medisim.presentation.components.CircleIconBackground
import com.example.medisim.presentation.components.CircleInitials
import com.example.medisim.presentation.components.ImageButtonClick
import com.example.medisim.presentation.components.TextLabel
import com.example.medisim.presentation.components.ViewImage
import com.example.medisim.ui.theme.CommonComponent2

@Composable
fun NavigationDrawerHeader(profileViewModel: ProfileViewModel) {
    profileViewModel.getUserInfo()
    val state = profileViewModel.state.value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 150.dp,
                )
            )
            .background(MaterialTheme.colorScheme.tertiary),

    ){
        Row(
            modifier = Modifier.padding(20.dp,top = 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            CircleInitials(name = state.userName)
            Column (
                modifier = Modifier.padding(start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

                ){
                TextLabel(
                    text = state.userName,
                    textFont = 18,
                    textFontWight = FontWeight.Bold
                )
                TextLabel(
                    text = state.email,
                    modifier = Modifier.padding(top = 10.dp),
                    textFont = 13,
                    textFontWight = FontWeight.Bold
                )
            }

        }
    }
}


@Composable
fun NavigationDrawerBody(navController: NavHostController,profileViewModel: ProfileViewModel) {
    val state = profileViewModel.state.value
    val context = LocalContext.current
    // Now can access resources using the context
    val resources = context.resources


    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.ar to "ar",
    ).mapKeys { stringResource(it.key) }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(start = 12.dp)){
        item {
            TextLabel(
                text = stringResource(R.string.general_settings),
                modifier = Modifier.padding(start = 3.dp, bottom = 10.dp),
                textFontWight = FontWeight.Bold,
                textFont = 18,
                textColor = MaterialTheme.colorScheme.secondary
            )
            BodyItem(
                item = DrawerItem.Language,
                title = stringResource(R.string.language),
                iconColor = DrawerItem.Language.color,
                onRowClick = {profileViewModel.onShowBottomSheet()}
            )

            BodyItem(
                item = DrawerItem.Mode,
                title = stringResource(R.string.dark_mode),
                iconColor = DrawerItem.Mode.color,
                isDark = state.isDark,
                onRowClick = {}
            ){profileViewModel.onChangeMode()}
            LineWithText(stringId = R.string.about)

            BodyItem(
                item = DrawerItem.TermsAndConditions,
                title = stringResource(R.string.terms_conditions),
                iconColor = DrawerItem.TermsAndConditions.color,
                onRowClick = {}
            )
            BodyItem(
                item = DrawerItem.License,
                title = stringResource(R.string.license),
                iconColor = DrawerItem.License.color,
                onRowClick = {}
            )
            BodyItem(
                item = DrawerItem.Rate,
                title = stringResource(R.string.rate_this_app),
                iconColor = DrawerItem.Rate.color,
                onRowClick = {}
            )
            BodyItem(
                item = DrawerItem.Share,
                title = stringResource(R.string.share_this_app),
                iconColor = DrawerItem.Share.color,
                onRowClick = {
                    shareText(context, "Check out this app!", "https://play.google.com/store/search?q=medithen+ai&c=apps")
                }
            )
            LineWithText(stringId = R.string.account_settings)
            BodyItem(
                item = DrawerItem.Logout,
                title = stringResource(R.string.log_out),
                iconColor = DrawerItem.Logout.color,
                onRowClick = {
                    profileViewModel.onLogoutClick(navController)
                }
            )
        }
        item{

            AnimatedVisibility(
                visible = state.isBottomSheetShow,
                ) {
                LanguageBottomSheet(
                    isArabic = state.isArabic,
                    onDismissRequest = { profileViewModel.onDismissRequest() },
                    onSelectLanguage = {selectionLocale->profileViewModel.onSelectLanguage(localeOptions[selectionLocale]!!,resources)}

                )
            }
        }

    }

}


@Composable
fun BodyItem(
    item: DrawerItem,
    title: String,
    iconColor:Long,
    isDark:Boolean = true,
    onRowClick:()->Unit,
    onModeSwitch:()->Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clickable {
                onRowClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ){
        CircleIconBackground(
            imageVector = item.imageVector,
            modifier = Modifier.background(Color(iconColor), CircleShape),
            iconColor = Color.White,
            iconSize = 40
        )
        TextLabel(
            text = title,
            modifier = Modifier.padding(start = 5.dp),
            textFont = 18,
            textFontWight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        if (title != stringResource(R.string.log_out)){
            if (title != stringResource(R.string.dark_mode)){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }else{
                Switch(
                    checked = isDark,
                    onCheckedChange = { onModeSwitch() }
                )
            }
        }

    }

}

@Composable
fun LineWithText(stringId:Int) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, start = 18.dp, end = 18.dp),
        color = MaterialTheme.colorScheme.primary
    )
    TextLabel(
        text = stringResource(stringId),
        modifier = Modifier.padding(start = 3.dp,bottom = 10.dp),
        textFontWight = FontWeight.Bold,
        textFont = 18,
        textColor = MaterialTheme.colorScheme.secondary
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    isArabic: Boolean,
    onDismissRequest: () -> Unit,
    onSelectLanguage:(String)->Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.height(350.dp),
        containerColor = MaterialTheme.colorScheme.background,

        ) {
        LanguageBottomSheetContent(
            isArabic = isArabic,
            onDismissRequest = { onDismissRequest() },
            onSelectLanguage = {selectionLocale-> onSelectLanguage(selectionLocale)}
        )

    }
}

@Composable
fun LanguageBottomSheetContent(
    isArabic:Boolean,
    onDismissRequest: () -> Unit,
    onSelectLanguage: (String) -> Unit,
    ) {
    Column (modifier = Modifier.padding(18.dp)){
        Row {
            TextLabel(
                text = stringResource(R.string.change_language),
                textFont = 26
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.HighlightOff,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        onDismissRequest()
                    },
                contentDescription = "",
                tint = CommonComponent2
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 30.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Row{
            LangCard(stringResource(id = R.string.en),R.drawable.en ,isArabic.not(),{ onDismissRequest()}){selectionLocale->
                onSelectLanguage(selectionLocale)
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            LangCard(stringResource(id = R.string.ar), R.drawable.ar ,isArabic,{ onDismissRequest()}){selectionLocale->
                onSelectLanguage(selectionLocale)
            }
        }

    }
}

@Composable
fun LangCard(
    langName:String,
    imageId:Int,
    isSelected:Boolean,
    onDismissRequest: () -> Unit,
    onSelectLanguage: (String) -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clickable {
                onDismissRequest()
                onSelectLanguage(langName)
            }
    ){
        ImageButtonClick(
            image = imageId,
            modifier = Modifier.clip(CircleShape),
            imageHeight = 50,
            imageWidth = 50,
            paddingValue = 5
        ) {}
        TextLabel(
            text = langName,
            modifier = Modifier.padding(start = 5.dp),
            textFont = 18,
            textFontWight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimatedVisibility(visible = isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "",
                tint = CommonComponent2
            )
        }
    }
}



fun shareText(context: Context, text: String, url: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$text \n $url")
        putExtra(Intent.EXTRA_STREAM, R.drawable.logooo)
        type = "text/plain"

        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

    }
    val shareIntent = Intent.createChooser(sendIntent, "Share App Via:")
    context.startActivity(shareIntent)
}